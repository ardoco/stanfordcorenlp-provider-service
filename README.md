# StanfordCoreNLP-Provider-Service

This RESTful web service receives text as input and returns the annotated text in JSON format.
The response follows the JSON schema specified on https://github.com/ArDoCo/textprovider-json.
The annotation is performed using Stanford CoreNLP.

# Usage

Once the microservice is up and running, you can send a GET request with the text you want to annotate to the following endpoint:
```
GET http://localhost:8080/stanfordnlp?text=Marie%20was%20born%20in%20Paris.
```

The microservice will return a JSON response containing the annotated text. Here's an example response:

```
{"sentences":[{"constituencyTree":"(ROOT (S (NP (NNP Marie)) (VP (VBD was) (VP (VBN born) (PP (IN in) (NP (NNP Paris))))) (. .)))","sentenceNo":0,"text":"Marie was born in Paris.","words":[{"id":0,"incomingDependencies":[],"lemma":"Marie","outgoingDependencies":[],"sentenceNo":0,"text":"Marie","posTag":"NNP"},{"id":1,"incomingDependencies":[],"lemma":"be","outgoingDependencies":[],"sentenceNo":0,"text":"was","posTag":"VBD"},{"id":2,"incomingDependencies":[],"lemma":"bear","outgoingDependencies":[{"targetWordId":4,"dependencyType":"OBL"},{"targetWordId":5,"dependencyType":"PUNCT"}],"sentenceNo":0,"text":"born","posTag":"VBN"},{"id":3,"incomingDependencies":[{"sourceWordId":4,"dependencyType":"CASE"}],"lemma":"in","outgoingDependencies":[],"sentenceNo":0,"text":"in","posTag":"IN"},{"id":4,"incomingDependencies":[{"sourceWordId":2,"dependencyType":"OBL"}],"lemma":"Paris","outgoingDependencies":[{"targetWordId":3,"dependencyType":"CASE"}],"sentenceNo":0,"text":"Paris","posTag":"NNP"},{"id":5,"incomingDependencies":[{"sourceWordId":2,"dependencyType":"PUNCT"}],"lemma":".","outgoingDependencies":[],"sentenceNo":0,"text":".","posTag":"."}]}]}
```
