# Builds all versions and outputs them to ./out

$current_dir = Get-Location
$forge_versions = @("1.12.2", "1.15.2")

function Format-Dir {
    param (
        [string]$dirName
    )
    
    if (Test-Path .\$dirName) {
        Set-Location .\$dirName
        
        Remove-Item * -Force -Recurse

        Set-Location $current_dir
    } else {
        mkdir $dirName
    }
}

function Build-Forge {
    param (
        $mc_version
    )

    Set-Location "./$mc_version"    
    Write-Output "Building forge mod for $mc_version`n"

    ./gradlew build
    
    Set-Location $current_dir
    Copy-Item "./$mc_version/build/libs/tpsmod-1.0.jar" -Destination "./out/tpsmod-$mc_version-1.0.jar"
    Write-Output "Finished building forge mod for $mc_version`n"
}

Write-Output "Building all versions and outputting to ./out`n"
Write-Output "Preparing ./out directory`n"
Format-Dir("out")
Write-Output "`nPrepared ./out directory`n"

foreach($forge_version in $forge_versions) {
    Build-Forge($forge_version)
}

Write-Output "`nFinished building all versions"
