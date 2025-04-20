param($in = "all_tests")

function compile_and_build {
    Write-Output "Compiling Java files..."
    # Create output directory if it doesn't exist
    if (-not (Test-Path "out")) {
        New-Item -ItemType Directory -Path "out"
        Write-Output "Created output directory"
    }

    # Compile Java files with specific source and target version
    javac  -cp src -d out src\*.java src\model\*.java src\view\*.java
    if ($LASTEXITCODE -ne 0) {
        Write-Output "Compilation failed!"
        return $false
    }

    Write-Output "Compilation successful."
    return $true
}

function run {
    param ($name)
    $in = "tests/$name.in"
    $out = "tests/$name.out"
    $exp = "tests/$name.exp"

    # Check if files exist
    if (-not (Test-Path $in)) {
        Write-Output "Error: Input file $in does not exist."
        return
    }
    if (-not (Test-Path $exp)) {
        Write-Output "Error: Expected output file $exp does not exist."
        return
    }

    # Run the test - pass the input file to the program
    Write-Output "Running test: $name"

    # Run directly from the class files instead of using JAR
    Get-Content $in | java -cp out Main > $out

    # Compare results
    if (Test-Path $out) {
        if (-not (Test-Path $out) -or -not (Test-Path $exp)) {
            Write-Output "Error: Missing test output or expected file."
            return
        }

        if ((Get-Content $out).Count -eq 0 -or (Get-Content $exp).Count -eq 0) {
            Write-Output "Error: One of the test files is empty."
            return
        }

        $res = Compare-Object -SyncWindow 0 (Get-Content $out) (Get-Content $exp)
        if ([string]::IsNullOrEmpty($res)) {
            Write-Output "Test successful."
        } else {
            Write-Output "Test failed."
            Compare-Object -SyncWindow 0 -IncludeEqual (Get-Content $out) (Get-Content $exp)
        }
    } else {
        Write-Output "Error: Output file was not created."
    }
}

# Create tests directory if it doesn't exist
if (-not (Test-Path "tests")) {
    New-Item -ItemType Directory -Path "tests"
    Write-Output "Created tests directory"
}

# First compile and build the code
$buildSuccess = compile_and_build
if (-not $buildSuccess) {
    Write-Output "Cannot run tests because compilation failed."
    exit 1
}

# Run tests
if ($in -eq "all_tests") {
    $names = (Get-ChildItem -Path "tests" -Filter "*.in" | ForEach-Object { $_.BaseName }) | Select-Object -Unique
    if ($names.Count -eq 0) {
        Write-Output "No test files found in the tests directory."
    } else {
        foreach ($n in $names) {
            Write-Output "-------------------------------"
            Write-Output "Test: $n"
            run $n
        }
    }
} else {
    run $in
}