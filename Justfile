
default: package

package:
  sbt package

test:
  sbt test

# install system dependencies using asdf
install-dependencies:
  #!/bin/bash -e
  if ! command -v asdf &> /dev/null
  then
    echo "This project installs system dependencies (such as java and sbt) using asdf."
    echo
    read -p "Do you want to install asdf? (y/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]
    then
      brew install asdf
    fi
  else
    echo "asdf is already installed."
  fi
  asdf plugin add java https://github.com/halcyon/asdf-java.git
  asdf plugin add sbt https://github.com/sbt/asdf-sbt.git
  asdf install

wipe:
  rm -rf .bsp target project/target .scala-build
