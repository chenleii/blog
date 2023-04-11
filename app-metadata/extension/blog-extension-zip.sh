#!/bin/bash

if [ ! $1 ]; then
  echo "Please \$1 enter password."
  exit 1
fi

echo "run: 7z a -tzip -p$1 -mem=AES256 -r ./blog-extension.zip ../../blog-extension  -xr!*/target | grep archive"
7z a -tzip -p$1 -mem=AES256 -r ./blog-extension.zip ../../blog-extension  -xr!*/target | grep archive