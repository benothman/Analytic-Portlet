#!/bin/sh
#
#
#
#

echo "cleaning workspace\n"
mvn clean
echo "\nAdding new files\n"
git add *
echo "\nCommiting to the local repository\n"
git commit -a -m "$1"
echo "\nCommiting to the remote repository\n"
git push origin master