﻿using System;
using System.Diagnostics;
using System.IO;

namespace ServerStarter
{
    class Program
    {
        static void Main(string[] args)
        {
            String flag = ".flag";
            String lastUP = "LatestUpdate";
            String flagDir = Path.Combine(Environment.CurrentDirectory, flag);
            String lastDir = Path.Combine(Environment.CurrentDirectory, lastUP);
            var os = Environment.OSVersion;
            Console.WriteLine("Platform: {0:G}", os.Platform);
            Process myProcess = new Process();
            if (os.Platform.ToString().StartsWith("Win"))
            {
                Console.WriteLine(File.Exists(flagDir));
                if (File.Exists(flagDir))
                {
                    try
                    {
                        String r = Path.Combine(Environment.CurrentDirectory, "Updator");
                        r = Path.Combine(r, "runner");
                        r = Path.Combine(r, "win-x64");
                        r = Path.Combine(r, "JUpdator.exe");
                        Process up = Process.Start(r);
                        up.WaitForExit();
                        File.Delete(flagDir);
                        Console.WriteLine("Deleting flag");
                        File.Delete(lastDir);
                        Console.WriteLine("Deleting last update date");
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e.ToString());
                    }

                }
            }
            else
            {
                if (File.Exists(flagDir))
                {
                    try
                    {
                        String r = Path.Combine(Environment.CurrentDirectory, "Updator");
                        r = Path.Combine(r, "runner");
                        r = Path.Combine(r, "linux-x64");
                        r = Path.Combine(r, "JUpdator");
                        Process up = Process.Start(r);
                        up.WaitForExit();
                        File.Delete(flagDir);
                        Console.WriteLine("Deleting flag");
                        File.Delete(lastDir);
                        Console.WriteLine("Deleting last update date");
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e.ToString());
                    }

                }

            }
            try
            {
                ProcessStartInfo startInfo = new ProcessStartInfo
                {
                    UseShellExecute = false,
                    CreateNoWindow = true
                };
                myProcess.StartInfo = startInfo;
                myProcess.StartInfo.FileName = @"java";
                String JAR = @"JRemoteControl.jar";
                String JARDIR = Path.Combine(Environment.CurrentDirectory, JAR);
                JARDIR = "-jar " + JARDIR;
                myProcess.StartInfo.Arguments = JARDIR;
                Console.WriteLine(JARDIR);
                myProcess.Start();
                myProcess.WaitForExit();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

        }
    }
}
