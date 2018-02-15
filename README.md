## hwscraper
Webscraper for hardverapro.hu. Performs searches, detects new ads and price changes, sends
the results in email.

A single execution runs the search tasks, saves the results, and sends an email only if there was
a change in the ads since the previous execution.

## Configuration
A sample configuration file is provided in src/resources/application.yml.default.

| Property           | Description                                                |
| ------------------ |----------------------------------------------------------- |
| spring.mail.*      | Your mail server/provider to send the mails from           |
| recipient          | Email address where the result mail will be sent           |
| subject            | Subject field of the result mail                           |
| baseUrl            | URL prefix for the ads, default is https://hardverapro.hu/ |
| userAgent          | User-Agent header to use during scraping                   |
| searchDelay        | Time to wait between performing the searches               |
| dbFile             | File path of the saved saved ads                           |
| searchTasks        | List of search links, see below                            |

### searchTasks format
Every search has an arbitrary title and a a search URL, which is simply copied from the
browser after perfming a search on the site.
Eg.:

searchTasks:
  - name: Any title for the search
    url: https://harverapro.hu/?....
  - name: Second search
    url: https://harverapro.hu/?....

## Example mail output
![Example mail](mail_body_example.png?raw=true "Example mail")