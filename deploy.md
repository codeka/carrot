# Deploying Carrot

(These instructions are mostly just for me, as I'm always forgetting the exact
steps...)

## Prerequisites

Make sure the global gradle.propties file has all the signing details set up (on
Windows, that's C:\Users\dean\.gradle\gradle.properties, on Mac/Linux that's
~/.gradle/gradle.properties). The contents of the file are saved in Keep.

## Building/Deploying

  1. Update all the references to version numbers
  
    README.md
    src/docs/getting-started.html
    gradle.properties

  2. Build/Upload

    # gradlew uploadArchives

  3. Publishing
     1. Go to https://oss.sonatype.org
     2. Find "aucomcodeka" entry
     3. Open up the contents, make sure all the JARs and so on are there
     4. Close it, wait a bit
     5. Release it, wait a bit

  4. Create a new entry in https://github.com/codeka/carrot/releases. The compiled .jar
     can be found in build\libs, and should be attached to the release. Otherwise, mostly
     just cut'n'paste from the previous release and add details about what's changed.
