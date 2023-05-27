import os

try:
    dirname = os.path.dirname(os.path.abspath(__file__))
    os.chdir(os.path.join(dirname, 'headshot'))
except:
    print("There is an issue with navigating to the headshot folder. Please make sure the folder exists in the main directory.")

for file in os.listdir():


    end = file.find('_')


    netid = file[:end]
    print(netid)

    if '.jpeg' in file:
        new = netid + '.jpg'
        os.rename(file,new)
    if '.png' in file:
        new = netid + '.jpg'
        print(new)
        os.rename(file,new)
        print(file)
    if '.jpg' in file:
        new = netid + '.jpg'
        os.rename(file,new)
    if '.JPG' in file:
        new = netid + '.jpg'
        os.rename(file,new)