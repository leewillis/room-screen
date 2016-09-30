from tkinter import *

class MyApp:
    def __init__(self, myParent):
        self.myContainer = Frame(myParent)
        self.myContainer.pack()

        self.button1 = Button(self.myContainer)
        self.button1["text"]="Hello"
        self.button1["background"] = "green"
        self.button1.pack()

        self.button2 = Button(self.myContainer)
        self.button2.configure(text="Hello again", background="tan")
        #self.button2.configure()
        self.button2.pack()


root = Tk()
myApp = MyApp(root)
root.mainloop()