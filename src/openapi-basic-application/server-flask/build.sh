#!/bin/bash
set -euo pipefail

rm -rf ./target
java -jar ../openapi-generator-cli.jar generate -i ../api.yaml -o ./target -g python-flask -c config.json

# The 7.22 version of the generator uses Workzeug 2.2 which is not compatible with Python 3.14.
python3.10 -m venv .venv
. .venv/bin/activate

pip install -r target/requirements.txt
# pip install -r target/requirements.txt -c requirements.txt
# pip install -r requirements.txt
