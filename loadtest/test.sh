#!/usr/bin/env bash


# No Hystrix
loadtest -n 50 -c 5 "http://localhost:8080/v2/service?timeout=200&errors=0&input=1"


loadtest -n 1000 -c 20 "http://localhost:8080/v2/service?timeout=200&errors=70&input=1"


# ---------------------------------------------------------------------------------------------------

# w/ Hystrix
loadtest -n 50 -c 5 "http://localhost:8080/v2/serviceHystrix?timeout=200&errors=0&input=1"


loadtest -n 4000 -c 20 "http://localhost:8080/v2/serviceHystrix?timeout=200&errors=70&input=1"


loadtest -n 5000 -c 20 "http://localhost:8080/v2/serviceHystrix?timeout=200&errors=70&input=1"

