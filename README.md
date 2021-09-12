# accuweather
The purpose of this repo is maintain the automation scripts for accuweather project

Install below prerequisite softwares"
1) Eclipse IDE(Version: Oxygen.3a Release (4.7.3a))
2) jdk1.8.0_211
3) TestNG plugin

Steps to Configure and Run:
1) clone the repository from https://github.com/vishnurevuri/accuweather.git
2) Import the project into eclipse as Maven Project.
3) Build the Project
4) Run the test from "\src\test\resources\suites\TestSuite.xml"suite file and provide run configuration details as  "-Denv=UAT"

Analyze the TestResults:
1) After successful execution, go to "\accuweather\reports" and verify the "report_*.html"
