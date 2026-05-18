
default: package

clean:
  sbt clean

package:
  sbt "core / package"

test:
  sbt test

bench:
  sbt "bench / Jmh / run -prof gc -i 10 -wi 5 -r 10s -w 10s -f 1 -t 1"

bench-quick:
  sbt "bench / Jmh / run -prof gc -i 5 -wi 3 -r 500ms -w 500ms -f 1 -t 1"

pages:
  sbt "samples / runMain hotmetal.samples.WriteSamplePages"

bench-json:
  mkdir -p benchmarks/results
  sbt "bench / Jmh / run -prof gc -i 10 -wi 5 -r 10s -w 10s -f 1 -t 1 -rf json -rff benchmarks/results/latest.json"

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
