#!/usr/bin/env bash

# No Hystrix, fast response
loadtest -n 50 -c 10 "http://localhost:8080/v2/service?timeout=200&errors=0&input=1"

# No Hystrix, with errors
loadtest -n 50 -c 10 "http://localhost:8080/v2/service?timeout=200&errors=50&input=1"