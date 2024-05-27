#!/bin/bash

LATEST_GIT_TAG=$(git tag -l --sort=-v:refname | grep -E '^[0-9]+\.[0-9]+\.[0-9]+$' | head -n 1)
PREVIOUS_TAG=$(git tag -l --sort=-v:refname | grep -E '^[0-9]+\.[0-9]+\.[0-9]+$' | head -n 2 | tail -n 1)

read -r -p "Creating GitHub Release for ${LATEST_GIT_TAG}? [Y/n] " choice
case "$choice" in
  y|Y ) ;;
  n|N ) exit;;
  * ) ;;
esac

CHANGELOG_FILE=$(mktemp -t q4c-changelog.XXX)
echo "$(date): Creating changelog file (${CHANGELOG_FILE})..."
cat > "${CHANGELOG_FILE}" << EOB
## Usage

[![Maven Central q4c repository](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://central.sonatype.com/artifact/io.github.tamingj.q4c/q4c)

\`\`\`xml
<dependency>
    <groupId>io.github.tamingj.q4c</groupId>
    <artifactId>q4c</artifactId>
    <version>${LATEST_GIT_TAG}</version>
</dependency>
\`\`\`

## Changelog
Changes in this Release: (\`${PREVIOUS_TAG}..${LATEST_GIT_TAG}\`)
EOB

if [ "${LATEST_GIT_TAG}" == "${PREVIOUS_TAG}" ]; then
  echo "$(date): Detected first release, creating changelog..."
  git log --pretty=format:'- %s' >> "${CHANGELOG_FILE}"
else
  echo "$(date): Detected a regular release, creating changelog..."
  git log "${PREVIOUS_TAG}..${LATEST_GIT_TAG}" --pretty=format:'- %s' >> "${CHANGELOG_FILE}"
fi


echo "$(date): Creating GitHub release with artifacts..."
gh release create "${LATEST_GIT_TAG}" --title "${LATEST_GIT_TAG}" --notes-file "${CHANGELOG_FILE}"
#gh release create "${LATEST_GIT_TAG}" --title "${LATEST_GIT_TAG}" --notes-file "${CHANGELOG_FILE}" "${ARTIFACTS_DIR}/*" #add files from https://repo1.maven.org/maven2/io/github/tamingj/q4c/q4c/${LATEST_GIT_TAG}/

echo "$(date): Removing tmp files..."
rm "${CHANGELOG_FILE}"
#rm -rf "${ARTIFACTS_DIR}"
