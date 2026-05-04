#!/bin/bash
set -euo pipefail

# The 7.22 version of the generator uses Workzeug 2.2 which is not compatible with Python 3.14.
python3.10 -m venv .venv
. .venv/bin/activate

PYTHONPATH=.:target python -m openapi_server
