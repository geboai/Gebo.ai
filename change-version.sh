#!/bin/bash
#!/bin/bash
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
echo "Changing from version $1 to $2"
find . -name pom.xml | xargs sed -i s/"$1"/"$2"/ 
sed -i s/"$1"/"$2"/ dockers/gebo.ai/Dockerfile
sed -i s/"$1"/"$2"/ dockers/gebo.ai/create-image.bat
find . -name pom.xml | xargs git stage 
sed -i s/"$1"/"$2"/ ./gebo.ui/package.json
git stage ./gebo.ui/package.json
find ./gebo.ui/projects -name package.json | xargs sed -i s/"$1"/"$2"/ 
find ./gebo.ui/projects -name package.json | xargs git stage
git stage dockers/gebo.ai/Dockerfile dockers/gebo.ai/create-image.bat
git commit -m"Changed version from $1 to $2"

