# Introduction
===============

E-commerce site is to be developed. Essential functionality includes catalog of products, search by catalog, personal accounts where client can manage order, get history of orders and update personal information.

User can manage basket before login or registration. All data must be stored in basket on the client side in this case. 
Information management involves update personal information (name, sername, login, birthday) and password;
The buying process includes filling a basket, address for delivery, type of payment;

Users with privileges of admin must be possible to add new product to catalog, create and manage categories of products, manage orders (change the delivery and payment state).

Also this type of users can access for statistics (top ten product, client, monthly and weekly revenue);
Search includes filtering by all parameters of products;

As addition second application is to be developed.
Essential functionality includes retrieving mobile plan usage statistics from the web service provided by the main application. Report in .pdf format must be generated using retrieved statistics.

=============== 

## Main characteristics
- Password hashed by bcrypt;
- Two roles:
 * Client
 * Admin;

- User can put products in the basket before login. This information stored in cookies on the client side. After login the products in the basket added into existing basket in the database or created new if it not;
- Validation is carried out both on the Client's and on the server side (Spring Validation);

- Admin can get statistics by top products, users and weekly/monthly revenue;
- Creation and management of product categories;
- Creation of new products;
- Change the status of existing orders;

![ScreenShot](https://github.com/fortochnik/tschool/blob/master/screenshot/main_page.png)

#	Technologies and frameworks
*	Java 1.8;
* MySQL;
*	Hibernate;
*	Spring MVC;
*	Spring Security;
* Spring Validation;
*	JSP, CSS3, JQuery;
*	JUnit;
*	Glassfish 4;
*	Mockito;
*	Log4j;

Also used in [additional part](https://github.com/fortochnik/pdfreport):
*	EJB;
*	JSF;

For more information see ["Technical Solution Description MMS"](https://github.com/fortochnik/tschool/blob/master/TechnicalSolutionDescriptionMMS.docx)
