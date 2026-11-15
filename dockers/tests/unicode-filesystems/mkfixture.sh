#!/bin/bash
# usage: mkfixture.sh <root> <names.txt>  - creates each file with its own relative name as content
set -eu
root="$1"; while IFS= read -r n; do [ -z "$n" ] && continue; mkdir -p "$root/$(dirname "$n")"; printf '%s' "$n" > "$root/$n"; done < "$2"
