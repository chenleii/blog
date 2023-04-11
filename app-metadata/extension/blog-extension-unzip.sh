#!/bin/bash

if [ ! $1 ]; then
  echo "Please \$1 enter password."
  exit 1
fi

echo "run: 7z x -P$1 blog-extension.zip -r -o../../ | grep archive"
7z x -P$1 blog-extension.zip -r -o../../ | grep archive
