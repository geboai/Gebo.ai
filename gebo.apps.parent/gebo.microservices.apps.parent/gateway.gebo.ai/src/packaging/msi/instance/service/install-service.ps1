$serviceName  = "GeboAIGateway"
$displayName  = "Gebo.ai API Gateway Service"
$description  = "Gebo.ai Spring Cloud Gateway (edge routing / load balancing)"
$exePath      = "$PSScriptRoot\..\..\..\gebo-ai-gateway.exe"   # adatta il path

# Se esiste già, fermalo e rimuovilo
$existing = Get-Service -Name $serviceName -ErrorAction SilentlyContinue
if ($existing) {
    Write-Host "Service $serviceName already exists, stopping and deleting..."

    try {
        Stop-Service -Name $serviceName -Force -ErrorAction SilentlyContinue
    } catch {
        Write-Host "Unable to stop service (maybe already stopped)"
    }

    # usa sc.exe delete per rimuovere la definizione
    sc.exe delete "$serviceName" | Out-Null
}

# Ora crei il servizio "pulito"
New-Service `
    -Name $serviceName `
    -BinaryPathName "`"$exePath`"" `
    -DisplayName $displayName `
    -Description $description `
    -StartupType Automatic

Write-Host "Service $serviceName created. It is NOT started automatically."
