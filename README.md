
# Telegram bot

This is my first pet project written in Java.\
The bot has several useful features:
- namely translating video and audio into text
- chatting with the chat GPT
- searching for vacancies on one of the most popular Ukrainian websites [DUO](https://dou.ua/)
- simple habit tracker (scheduler).


You can send short voice messages up to 1 minute and 10 MB to the bot, and within a few seconds, it will return the text.\
To communicate with GPT, I had to address the challenge of its memory, as described in this [Medium article](
https://medium.com/@agri.kridanto/chat-gpt-api-add-memory-and-context-of-discussion-6a18b92bd47a) \
To get the latest job vacancies, simply press a button and choose the language you are interested in (Java, C++, C#, Python).\
The tracker allows you to plan your day.
In the future, there are plans to enhance it by adding reminders for incomplete tasks and imposing time constraints on tasks.

***
For speech recognition, I used one of [Google's models](https://cloud.google.com/text-to-speech/docs/quickstarts),
as well as the language model [GPT-3.5-turbo-1106](https://platform.openai.com/docs/models/gpt-3-5) from OpenAI.


## Libraries:
In the project, I used the following additional libraries:
- [Jsoup](https://jsoup.org/)
- [Apache POI](https://poi.apache.org/components/spreadsheet/index.html)
- [TelegramBots](https://github.com/rubenlagus/TelegramBots)
- [google-cloud-speech](https://cloud.google.com/speech-to-text/docs/speech-to-text-client-libraries#client-libraries-resources-java)



## Properties file
An example of the properties file is provided below.
> telegram.bot.name= name\
> telegram.bot.token= tocken\
> gpt.key= openAI API Key\
> spring.datasource.url=jdbc:mysql://localhost:3306/bot\
> spring.datasource.username= root\
> spring.datasource.password= root