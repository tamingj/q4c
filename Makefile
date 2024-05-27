
release:
	mvn --batch-mode -P release release:clean release:prepare release:perform deploy