#!/bin/bash
#
# change-version.sh <old> <new> - move every file of this repository that carries the
# version from <old> to <new>, stage them and commit (nothing is pushed).
#
# <old> must be the exact current version, -SNAPSHOT suffix included: it is matched
# literally, and a bare 1.0.5.0 would also match inside 1.0.5.0-SNAPSHOT and leave
# 1.0.6.0-SNAPSHOT-SNAPSHOT behind.
#
cd "$(dirname "$0")"
export CURRENT_BRANCH=`git branch --show-current`
if [ "$CURRENT_BRANCH" = "develop" ]; then
echo "We are correctly in develop branch"
else
echo "We are in the $CURRENT_BRANCH but this script can be run only from develop branch"
exit -1
fi
if [ -n "$1"  ]
then
	echo "Old version: $1"
else
	echo "Required actual version as 1st parameter"
	exit 1
fi
if [ -n "$2" ]
then
	echo "New version: $2"
else
	echo "Required New version as 2nd parameter"
	exit 1
fi
OLD="$1"
NEW="$2"
# the dots escaped, so sed matches the old version literally
OLD_RE="${OLD//./\\.}"
if ! grep -q "<version>$OLD_RE</version>" pom.xml; then
	echo "pom.xml is not at version $OLD: pass the exact current version, -SNAPSHOT suffix included"
	exit 1
fi

# package-lock.json files: the root version and the root's tarball references follow
# package.json, and the locked @Gebo.ai/* libraries still at the old version are dropped -
# npm cannot compare their four-part versions with the new ones (it fails with "Invalid
# Version") and adds them back from the new tarballs on the next npm install.
update_locks() {
	node - "$OLD" "$NEW" "$@" <<'EOF'
const fs = require("fs");
const [oldV, newV, ...files] = process.argv.slice(2);
for (const file of files) {
	const lock = JSON.parse(fs.readFileSync(file, "utf8"));
	const root = lock.packages[""];
	if (lock.version === oldV) lock.version = newV;
	if (root.version === oldV) root.version = newV;
	for (const [name, spec] of Object.entries(root.dependencies || {}))
		root.dependencies[name] = spec.split(oldV).join(newV);
	for (const [path, entry] of Object.entries(lock.packages))
		if (path.startsWith("node_modules/@Gebo.ai/") && entry.version === oldV) delete lock.packages[path];
	fs.writeFileSync(file, JSON.stringify(lock, null, 2) + "\n");
}
EOF
}

echo "Changing from version $OLD to $NEW"
FILES=(
	$(git ls-files ':(glob)**/pom.xml')
	dockers/gebo.ai/Dockerfile dockers/gebo.ai/create-image.bat dockers/gebo.ai/*.sh
	dockers/easyinstall.gebo.ai/Dockerfile dockers/easyinstall.gebo.ai/create-image.bat dockers/easyinstall.gebo.ai/*.sh
	# Microservices stack: .env pins the image tag compose resolves for every
	# geboai/*.gebo.ai service, and the compose file repeats it as the per-service
	# fallback - both must track the bump or the stack silently runs the previous
	# release's images.
	dockers/gebo.microservices/.env dockers/gebo.microservices/docker-compose.yml
	gebo.ui/package.json
	$(git ls-files ':(glob)gebo.ui/projects/*/package.json')
	# Microservices Angular client package.json files (workspace and published library level)
	$(git ls-files ':(glob)gebo.api.clients/gebo.microservices.clients.parent/*/package.json')
	$(git ls-files ':(glob)gebo.api.clients/gebo.microservices.clients.parent/*/projects/*/package.json')
	run.bat
)
LOCKS=(
	gebo.ui/package-lock.json
	$(git ls-files ':(glob)gebo.api.clients/gebo.microservices.clients.parent/*/package-lock.json')
)
sed -i "s/$OLD_RE/$NEW/g" "${FILES[@]}"
update_locks "${LOCKS[@]}"
git stage "${FILES[@]}" "${LOCKS[@]}"
git commit -m"Changed version from $OLD to $NEW"
