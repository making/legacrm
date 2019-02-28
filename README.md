```
curl -s --header "content-type: text/xml" -d @request.xml http://localhost:8080 | xmllint --format -

curl -s --header "content-type: text/xml" -d @<(sed 's/0000000001/0000000002/g' request.xml) http://localhost:8080 | xmllint --format -
```