There are two components to AnimalProject:
* AnimalQuery is the original application. The user can enter as many entries for each animal or operation.
* AnimalKingdom which is supposed to be a SpringBoot web application that displays all animals and related data.

**Running AnimalQuery:**
1. Navigate to the directory with the AnimalQuery file
2. Run the java app with the desired entries: java AnimalQuery "Mr Pickles" cat
3. Results should print to the standard output
4. The application can also be opened and run in IntelliJ

**Running AnimalKingdom:**

It uses Gradle and Java 15 and below you'll find a list of libraries included in as gradle dependencies:
* junit-jupiter-api: 5.7.0
* junit-jupiter-engine: 5.7.0
* spring-boot-starter-web: 2.5.1
* gson: 2.8.6
* mockito-inline: 3.7.7

In the 'resources' directory, you'll find data.json. This file contains all my test data you don't have to do anything with it as it will be loaded on app startup.

I built and ran this using IntelliJ and didn't configure it for the sake of time, so when you start the application you simply should be able to navigate to your browser to localhost:8080.

**AnimalQuery**

In order to use the AnimalQuery follow the below format for a single entry:
"Animal Name" animalType favoriteFood height weight age

(ex. "Mr Pickles" cat catfood 12 12 3)

For multiple entries, separate each entry with a comma otherwise each value can be separated by a single space:

- "Mr Pickles" cat catfood 12 12 3,
"Bowser" dog dogfood 30 55 4,
"Moo" cow grass 5 1000 4,
"Butterfly" cupcakes 7 11 1235

All entries must contain a name and an animal type. All other data is optional, but cannot be placed out of order.

- "Mr Pickles" cat catfood 12 12 3,
"Bowser" dog dogfood
"Moo" cow

Values with any whitespace must be entered within double-quotes.

- "Mr Pickles"
"Filet Mignon"
"west african bull elephant"
  
Operations can be performed to add, update or remove animals from the current list of available animals.

- Add - adds an animal to the list of animals - associated fields: animal name, message related to the animal, isAquatic, isAerial, isExtraterrestrial, isMythological
- Remove - removes an animal from the list of animals - associated fields animal name
- Update - updates an existing animal - associated fields: animal name, message related to the animal, isAquatic, isAerial, isExtraterrestrial, isMythological
- Reset - resets the current list of animals to the default

To use an operation simply place a dash (-) in front of the key word. It will then be processed as a normal entry.

- -add monkey "oooo oooo aahh" false false false false
- -remove monkey
- -update unicorn neigh false false false false ***(For the purposes of this exercise we genetically engineered a unicorn)***
- -reset

Please reach out to me if you have any issues running the project. I'd be more than happy to look at the issue.
