#conda install poppler
#pip install pdf2files
#pip install poppler-utils


from pdf2image import convert_from_path
import os

try:
    dirname = os.path.dirname(os.path.abspath(__file__))
    os.chdir(os.path.join(dirname, 'resume'))
except:
    print("There is an issue with navigating to the resume folder. Please make sure the folder exists in the main directory.")

for file in os.listdir():
    print(file)
    if file != '.DS_Store':
        pages = convert_from_path(file,500)
        index = file.find("_")

        os.chdir(os.path.join(dirname, 'resume_convert'))
        for page in pages:
            page.save(file[:index]+'.png', 'PNG')
    
        os.chdir(os.path.join(dirname, 'resume'))
    