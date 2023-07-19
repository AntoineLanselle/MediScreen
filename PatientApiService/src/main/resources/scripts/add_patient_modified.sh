#!/bin/bash

# Utiliser curl pour envoyer les données au format JSON

# Patient 1
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Gasnier",
  "given": "Lisa",
  "dob": "1966-12-31",
  "sex": "F",
  "address": "1 Brookside St",
  "phone": "100-222-3333"
}' http://localhost:8081/patient/add

# Patient 2
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Dubois",
  "given": "Lorie",
  "dob": "1945-06-24",
  "sex": "M",
  "address": "2 High St",
  "phone": "200-333-4444"
}' http://localhost:8081/patient/add

# Patient 3
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Dumoulin",
  "given": "Patrick",
  "dob": "2004-06-18",
  "sex": "M",
  "address": "3 Club Road",
  "phone": "300-444-5550"
}' http://localhost:8081/patient/add

# Patient 4
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "François",
  "given": "Jean",
  "dob": "2002-06-28",
  "sex": "F",
  "address": "4 Valley Dr",
  "phone": "400-555-6666"
}' http://localhost:8081/patient/add

# Patient 5
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Durand",
  "given": "Pierre",
  "dob": "1980-03-15",
  "sex": "M",
  "address": "5 Main St",
  "phone": "500-666-7777"
}' http://localhost:8081/patient/add

# Patient 6
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Lefevre",
  "given": "Marie",
  "dob": "1975-08-02",
  "sex": "F",
  "address": "6 Elm Ave",
  "phone": "600-777-8888"
}' http://localhost:8081/patient/add

# Patient 7
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Martin",
  "given": "Paul",
  "dob": "1990-11-20",
  "sex": "M",
  "address": "7 Oak St",
  "phone": "700-888-9999"
}' http://localhost:8081/patient/add

# Patient 8
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Gagnon",
  "given": "Sophie",
  "dob": "1988-04-10",
  "sex": "F",
  "address": "8 Maple Rd",
  "phone": "800-999-0000"
}' http://localhost:8081/patient/add

# Patient 9
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Tremblay",
  "given": "Robert",
  "dob": "1982-09-05",
  "sex": "M",
  "address": "9 Pine Ln",
  "phone": "900-000-1111"
}' http://localhost:8081/patient/add

# Patient 10
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Roy",
  "given": "Isabelle",
  "dob": "1995-02-25",
  "sex": "F",
  "address": "10 Birch St",
  "phone": "100-111-2222"
}' http://localhost:8081/patient/add

# Patient 11
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Cote",
  "given": "Eric",
  "dob": "1978-07-12",
  "sex": "M",
  "address": "11 Cedar Rd",
  "phone": "110-222-3333"
}' http://localhost:8081/patient/add

# Patient 12
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Morin",
  "given": "Caroline",
  "dob": "1984-12-08",
  "sex": "F",
  "address": "12 Willow Dr",
  "phone": "120-333-4444"
}' http://localhost:8081/patient/add

# Patient 13
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Lavoie",
  "given": "Michel",
  "dob": "1970-05-30",
  "sex": "M",
  "address": "13 Spruce Ave",
  "phone": "130-444-5555"
}' http://localhost:8081/patient/add

# Patient 14
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Gagne",
  "given": "Nathalie",
  "dob": "1972-10-16",
  "sex": "F",
  "address": "14 Fir St",
  "phone": "140-555-6666"
}' http://localhost:8081/patient/add

# Patient 15
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Pelletier",
  "given": "Richard",
  "dob": "1969-06-28",
  "sex": "M",
  "address": "15 Cedar Rd",
  "phone": "150-666-7777"
}' http://localhost:8081/patient/add

# Patient 16
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Rousseau",
  "given": "Catherine",
  "dob": "1989-03-12",
  "sex": "F",
  "address": "16 Maple Rd",
  "phone": "160-777-8888"
}' http://localhost:8081/patient/add

# Patient 17
curl -X POST -H "Content-Type: application/json" -d '{
  "family": "Simard",
  "given": "Sylvain",
  "dob": "1992-08-25",
  "sex": "M",
  "address": "17 Oak St",
  "phone": "170-888