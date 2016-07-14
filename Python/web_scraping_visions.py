#!/usr/bin/python3
# web_scraping_visions.py by Cara(WonKyoung) Chung
# 20 June 2015 Edited by     Cara(WonKyoung) Chung 

# tkinter : libraries of definiton a frame
# ttk     : It has window frame libraries
# messagebox : It has a message box libraries
import sqlite3
import tkinter as tk 
import traceback
from tkinter import *
from tkinter import ttk
from tkinter import messagebox
import requests  
from bs4 import BeautifulSoup

__author__ = "Won Kyoung Chung"

# Class ScrapingPage shows the first page to scrape data of web sites 
# ==== Data Base ====                                         
# department         : Department Name                        
# category_1         : First Category                         
# category_2         : Second Category                        
# p_name             : Product Name                                   
# p_sale_price       : Product Sale Price                           
# p_reg_price        : Product Regular Price                        
# a_free_shipping    : Availability -Free Shipping             
# a_sale_end_date    : Availability -Discount End Date
# a_web_only         : Availability -Web Only
# a_in_store_only    : Availability  - In Store Only

class ScrapingPage( Frame ):
    #Define a master frame : input fields and buttons
    def __init__(self):
        tk.Frame.__init__(self)
        #Define a title of top of the Frame
        self.master.title('Web Scraping System')
        #Unable to resize the Frame
        self.master.resizable(False, False)
        #Set the color of the Frame
        self.master.configure(background = '#e1d8b9')
        #Create an instance of ttk.Style() and call its configure()
        self.style = ttk.Style()
        self.style.configure('TFrame', background = '#e1d8b9')
        self.style.configure('TButton', background = '#e1d8b9')
        self.style.configure('TLabel', background = '#e1d8b9', font = ('Arial', 11))
        self.style.configure('Header.TLabel', font = ('Arial', 18, 'bold'))      
        #Set the frame header(Logo, image and text)
        self.frame_header = ttk.Frame(self.master)
        self.frame_header.pack()
        #Algonquin College Logo
        self.logo = PhotoImage(file = 'visions_logo .png')
        #Set the Header Label
        ttk.Label(self.frame_header, image = self.logo).grid(row = 0, column = 0, rowspan = 2)
        ttk.Label(self.frame_header, text = 'Scrape Visions Products Sales Information!', style = 'Header.TLabel').grid(row = 0, column = 1)
        ttk.Label(self.frame_header, wraplength = 300,
                  text = ("Thank you for choosing 360pi System for your good choice.")).grid(row = 1, column = 1)
        #Set the frame content(lables, entry fields and buttons)
        self.frame_content = ttk.Frame(self.master)
        self.frame_content.pack()
        

        #Submit Button
        ttk.Button(self.frame_content, text = 'Start Scraping!!!',
                   command = self.scrapy).grid(row = 10, column = 0, padx = 5, pady = 5, sticky = 'e')
        
        #Query button of scraping DB 
        ttk.Button(self.frame_content, text = 'Scraping Status',
                   command = self.listpage).grid(row = 10, column = 1, padx = 5, pady = 5, sticky = 'w')

    #Web Scrapting
    def scrapy(self):
        url        = "http://www.visions.ca/Default.aspx" 
        front_url  = "http://www.visions.ca"
        
        response = requests.get(url)
        soup = BeautifulSoup(response.content) 
        
        a_data = soup.find_all("a")
        for link in a_data:
            if "/Catalogue/Category/Default.aspx?categoryId" in link.get("href"):
                ch_cnt = len(link.text)
                if (link.text.upper() == link.text):
                    print ("\n============= Department ================>", link.text)
                    department = link.text
                elif (link.text.find(">") > 0):
                    print ("============= Category 1 ================>", link.text)
                    category_1 = link.text
                else:
                    print ("============= Category 2 ================>", link.text)
                    category_2 = link.text
                    url_2 = front_url + link.get("href")
                    response_2 = requests.get(url_2)
                    soup_2 = BeautifulSoup(response_2.content)
                    detail_data = soup_2.find_all("div",{"class":"contentright"})
                    for products_detail in detail_data:
                        try:
                            print ("===========Product Name ============" + products_detail.h2.text)
                            p_name = products_detail.h2.text
                        except:
                            pass
                        try:
                            print ("Sale    Price - " + products_detail.find_all("span",{"class":"price"})[0].text)
                            p_sale_price = products_detail.find_all("span",{"class":"price"})[0].text
                        except:
                            pass
                        try:
                            print ("Regular Price - " + products_detail.find_all("span",{"class":"price-old"})[0].text)
                            p_reg_price = products_detail.find_all("span",{"class":"price-old"})[0].text
                        except:
                            pass
                        try:
                            print("=========== Availibility ==========")
                            for i in range(0,7):
                               if ("Shipping" in products_detail.find_all("span")[i].text):
                                   print("1. Shipping      : " + products_detail.find_all("span")[i].text)
                                   a_shiping = products_detail.find_all("span")[i].text
                               elif ("Ends" in products_detail.find_all("span")[i].text): 
                                   print("2. Sale End Date : " + products_detail.find_all("span")[i].text)
                                   a_sale_end_date = products_detail.find_all("span")[i].text  
                               elif ("Web" in products_detail.find_all("span")[i].text):           
                                   print("3. Web Only      : " + products_detail.find_all("span")[i].text)
                                   a_web_only = products_detail.find_all("span")[i].text
                               elif ("local store" in products_detail.find_all("span")[i].text): 
                                   print("4. In Store Only : " + products_detail.find_all("span")[i].text)
                                   a_in_store_only = products_detail.find_all("span")[i].text
                               else:
                                   pass
                            #Insert a record
                            self.databaseInsert()
                        except:
                            pass       
                           
    ##Submit the currently rows
    #def submit(self):
    #    self.scrapy()
    #    #Clear the all input fields  
    #    #self.clear()
        
    # Clear the fields(Name, email, comments)
    def clear(self):
    # Delete Query
        print("Need to clear db")
        
    # Call the StatusPage class
    def listpage(self):
        self.newWindow = StatusPage()   
        
    # Connect to Database   
    def databaseInsert(self):
    #Connect to the Database(Sqlite3)
        db = sqlite3.connect('scraping.db')
        db.row_factory = sqlite3.Row
        try:
            db.execute('create table  if not exists scraping\
                                                  (department text,category_1      text,category_2      text,p_name     text,p_sale_price    text,\
                                                  p_reg_price text,a_free_shipping text,a_sale_end_date text,a_web_only text,a_in_store_only text)')

        except:
            print('Exception - To create a table(databaseInsert)')
            
        try:
            db.execute('insert into scraping (department,category_1,category_2,p_name,p_sale_price,p_reg_price,\
                                                a_free_shipping,a_sale_end_date,a_web_only,a_in_store_only) values (?,?,?,?,?,?,?,?,?,?)',
                   (self.department.get(), self.category_1.get(),self.category_2.get(),self.p_name.get(),
                    self.p_sale_price.get(), self.p_reg_price.get(),self.a_free_shipping.get(), self.a_sale_end_date.get(),
                    self.a_web_only.get(),self.a_in_store_only))
            db.commit()
            cursor = db.execute('select * from scraping')
            for row in cursor:
                print(row['department'],row['category_1'],row['category_2'],row['p_name'],row['p_sale_price'],
                      row['p_reg_price'],row['a_free_shipping'],row['a_sale_end_date'],row['a_web_only'],row['a_in_store_only'])
        except sqlite3.OperationalError as e:
            print('Could not insert a record Exception : ', e)

        
# Show the today's scraping status: Second Page
class StatusPage(Frame):
    #Define initial second Frame
    def __init__(self):
        second = tk.Frame.__init__(self)
        second = Toplevel(self)
        #Define a title of top of the Frame
        second.title('Web scraping System')
        #Unable to resize the Frame
        second.resizable(False, False)
        #Set the color of the Frame
        second.configure(background = '#e1d8b9')
        #Create an instance of ttk.Style() and call its configure()
        self.style = ttk.Style()
        self.style.configure('TFrame', background = '#e1d8b9')
        self.style.configure('TButton', background = '#e1d8b9')
        self.style.configure('TLabel', background = '#e1d8b9', font = ('Arial', 11))
        self.style.configure('Header.TLabel', font = ('Arial', 18, 'bold'))      
        #Set the frame header(text)
        self.frame_header = ttk.Frame(second)
        self.frame_header.pack()
        self.logo = PhotoImage(file = 'visions_logo .png')
        ttk.Label(self.frame_header, image = self.logo).grid(row = 0, column = 0, rowspan = 2)
        ttk.Label(self.frame_header, text = 'scraping Status!!', style = 'Header.TLabel').grid(row = 0, column = 1)
        ttk.Label(self.frame_header, wraplength = 300,
                  text = ("Visions.ca scraping Status! ")).grid(row = 1, column = 1)

 
        #Set the frame content(lables, entry fields and buttons)
        self.frame_content = ttk.Frame(second)
        self.frame_content.pack()
        #Set the header of status page
        ttk.Label(self.frame_content, text = 'Department Name      ').grid(row = 0, column = 0, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'First Category       ').grid(row = 0, column = 1, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'Second Category      ').grid(row = 0, column = 2, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'Product Name         ').grid(row = 0, column = 3, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'Product Sale Price   ').grid(row = 0, column = 4, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'Product Regular Price').grid(row = 0, column = 5, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'Free Shipping        ').grid(row = 0, column = 6, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'Discount End Date    ').grid(row = 0, column = 7, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'Web Only ').grid(row = 0, column = 8, padx = 2, sticky = 'w')
        ttk.Label(self.frame_content, text = 'In Store Only').grid(row = 0, column = 9, padx = 2, sticky = 'w')


      
        #Read the table 'scraping'
        self.databaseRead()


    # Connect to Database   
    def databaseRead(self):
    #Connect to the Database(Sqlite3)
        db = sqlite3.connect('scraping.db')
        db.row_factory = sqlite3.Row
        try:
            cursor = db.execute('select * from scraping')     
            rownum = 0
            for row in cursor:
                #Set the output fields of the status
                rownum = rownum + 1
                ttk.Label(self.frame_content, text = str(row['department      '])).grid(row = rownum, column = 0, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['category_1      '])).grid(row = rownum, column = 1, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['category_2      '])).grid(row = rownum, column = 2, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['p_name          '])).grid(row = rownum, column = 3, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['p_sale_price    '])).grid(row = rownum, column = 4, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['p_reg_price     '])).grid(row = rownum, column = 5, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['a_free_shipping '])).grid(row = rownum, column = 6, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['a_sale_end_date '])).grid(row = rownum, column = 7, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['a_web_only      '])).grid(row = rownum, column = 8, padx = 2, sticky = 'w')
                ttk.Label(self.frame_content, text = str(row['a_in_store_only '])).grid(row = rownum, column = 8, padx = 2, sticky = 'w')
            #ttk.Label(self.frame_content, text = str(line) + '\n').grid(row = rownum, column = 0, padx = 3, sticky = 'sw')
        except sqlite3.OperationalError as e:
            print('Could not read a table exception : ' ,e)
            #sys.stderr.write('ERROR: %sn' % str(err))
        
        print('The End.')
              
#Define main         
def main():

    ScrapingPage().mainloop()
    
#Finish the python program    
if __name__ == '__main__': main()
