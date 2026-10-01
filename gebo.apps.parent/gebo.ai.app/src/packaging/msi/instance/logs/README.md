# Logs Directory

This directory is required for the Windows service installation. The JVM options configured in the packaging profile include `-Xlog:gc:file=$APPDIR\instance\logs\gc.log:...`, which writes the garbage-collection log to this location.

The JVM does not create this directory at runtime and refuses to start if it cannot open the log file. The Windows service launcher invokes the JVM directly, so a missing `logs/` directory causes the service to fail outright with "Could not create the Java Virtual Machine".

This directory must ship with every Windows MSI installation, even though it may be empty at install time. The GC log will be created when the service starts.
