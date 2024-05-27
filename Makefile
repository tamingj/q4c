
release:
	mvn --batch-mode -P release release:clean release:prepare release:perform

create-gh-release:
	@./scripts/create-gh-release.sh
