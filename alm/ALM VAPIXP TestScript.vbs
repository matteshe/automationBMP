'*****************************************************************************
' Call to include our external FW script and execute the primary
' function "SoapUI"
'*****************************************************************************
Sub Test_Main(Debug, CurrentTestSet, CurrentTSTest, CurrentRun)
    'include the framework VBScript
    Include "U:\Eigene Dateien\NetBeansProjects\BMP\alm\FW.vbs"
    'Call the primary ("renamed Main") function
    RunTest TDOutput, Debug, CurrentTestSet, CurrentTest, CurrentRun, ThisTest, ID
End Sub

'*****************************************************************************
' include VBScript file and allow Subs and functions to be executed
'*****************************************************************************
Sub Include(file)
    Dim fso, f
    Set fso = CreateObject("Scripting.FileSystemObject")
    Set f = fso.OpenTextFile(file, 1)
    str = f.ReadAll
    f.Close
    ExecuteGlobal str
End Sub