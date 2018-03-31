#!/usr/bin/env bash

#curl -v "http://localhost:8080/v2/serviceHystrix?timeout=2000&errors=0&input=1"

loadtest -n 100000 -c 3 "http://localhost:8080/v2/serviceHystrix?timeout=2000&errors=0&input=1"