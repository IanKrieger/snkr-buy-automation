## Overview
- This app can be used to buy shoes from a certain website without any manual intervention.
- There are a few very easy modifications you have to make, that only require 3 downloads.
- It also requires a way to edit some text, also very easy to do 👍.

### Things to download
- Java: Instructions for your OS can be found [here](https://www.java.com/en/download/help/download_options.html).
- Maven: Install Maven with instructions found [here](http://maven.apache.org/install.html).
- Git: Instructions for your OS can be found [here](https://git-scm.com/downloads).

#### Instructions
- You have found your way here, so you might know what you are doing.
- If not:
    - Create github acct
    - Clone repo in a directory of your choice: `git clone https://github.com/IanKrieger/java-buy-bot.git`
- Edit `application.properties`. Once done, make sure to save it.
    - `shoe.buy.url = <shoe-url-to-buy>`. Shoe you want to buy.
        - i.e: `https://www.nike.com/launch/t/air-jordan-5-anthracite`
    - `login.username = <yourEmail>`. Login email.
    - `login.password = <yourPassword>`. Login psswd.
    - `shoe.size = M 10`. Make sure shoe size specifies gender before, otherwise it will select first button with 10.
        - Valid Combos: `M 10`, `W 11.5`, `M 10 / W 11.5`
    - `credit.card.ccv = <ccv>`. This step assumes you have a credit card setup in your acct. Do that, it's easier.
    - `buy.shoe = false`. Switch this to true if you want an actual chance of buying shoe.
    - `threads`. How many distinct instances to try.
    
- Make sure all above steps followed. Once done go to the java-buy-bot directory:
    - run `mvn clean install`
    - `java -jar target/buy-bot.jar`
  
If all goes well, you should be in line and can get the shoes.