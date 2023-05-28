# GCC_recruitment_portal

Outputs readable powerpoint format of applicants information, headshorts, resume, attendance, and short answer responses. 
This is for easier information processing during recruitment deliberations.

Users must first run these commands in their command shell in order to correctly run "converttopdf.py"
  conda install poppler
  pip install pdf2files
  pip install poppler-utils
  
From the Google Form responses, users must download the resume folder, headshot folder, and the excel sheet of responses. The folders
must be renamed to "resume" and "headshot". The excel sheet must be renamed to 'input.csv'.

To create the Attendance.csv, all excel sheets of the applicants attendance from recruitment events must be downloaded and placed into the folder
titled 'collect_attendance'. The left-most column of each of the sheets must contain the applicants net IDs. Running the sheets through
'collect_attendance' should output a sheet with all the netIDs attendance at various recruitment events.

Following this, in the command shell, first type the command 
  python converttopdf.py
  python rename.py
  python app.py
  
These commands should output a test.pptx with the powerpoint containing the applicant data. 


