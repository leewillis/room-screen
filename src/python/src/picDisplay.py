# import urllib.request
import requests
import json
import base64
import time
from tkinter import *
#import tkinter as tk
from PIL import ImageTk, Image
from io import BytesIO

class Application:

    def key(p1, p2):
        if p2.keysym=="Escape":
            quit()

    def __init__(self, parent):
        self.container = Frame(parent)
        self.container.pack(fill=BOTH, expand=YES)

        self.canvas = Canvas(self.container, bd=0, highlightthickness=0, background="black")
        #self.container.bind("<Key>", self.key)
        #self.canvas.focus_set()
        self.canvas.pack(fill=BOTH, expand=YES)

        self.showPicture()
        #photo = self.getPicture()
        #self.showPicture(photo["imageAsB64"], self.canvas)

    def showPicture(self):

        picture = requests.get("http://gibson:8080/nextPicture").content
        content = picture.decode("utf-8")

        photo = json.loads(content)
        print(photo["fullFileName"])
        b64 = photo["imageAsB64"]

        im = Image.open(BytesIO(base64.b64decode(b64)))
        owidth, oheight = im.size

        ir = oheight / owidth
        #these never change...
        sw = root.winfo_screenwidth()
        sh = root.winfo_screenheight()
        sr = sh / sw

        if ir >= sr:
            nh = sh
            nw = (int) (owidth * (sh /  oheight))
        else:
            nh = (int) (oheight * (sw / owidth))
            nw = sw

        im = im.resize((nw, nh),  Image.ANTIALIAS)
        self.canvas.image = ImageTk.PhotoImage(im)
        #canvas.image.zoom(.5, .5)
        l = (int) ((sw - nw) / 2)
        t = (int) ((sh - nh) / 2)
        self.canvas.create_image(l, t, anchor='nw', image=self.canvas.image)
        interval = root.after(20000, self.showPicture)

    def getPicture(self):
        #picture = urllib.request.urlopen("http://gibson:8080/nextPicture").read()
        picture = requests.get("http://gibson:8080/nextPicture").content
        content = picture.decode("utf-8")
        print(content)
        return json.loads(content)


root = Tk()
root.attributes("-fullscreen", True)
root.config(cursor="none")

#root.bind("<Button>", quit())

app = Application(root)
root.bind("<Key>", app.key)
root.mainloop()



# class PicDisplay:
#     def getPicture(self):
#         #picture = urllib.request.urlopen("http://gibson:8080/nextPicture").read()
#         picture = requests.get("http://gibson:8080/nextPicture").content
#         content = picture.decode("utf-8")
#         print(content)
#         return json.loads(content)
#
#     def showPicture(self, b64):
#         bytes = base64.b64decode(b64)
#         f = open("tmp.jpg", "wb")
#         f.write(bytes)
#         f.close()
#         with Image.open("tmp.jpg") as img:
#
#             #img.show();
#
#
# p = PicDisplay()
# photo = p.getPicture()
# print (photo)
# p.showPicture(photo["imageAsB64"])
#
