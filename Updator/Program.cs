﻿using System;
using System.Net;
using System.IO;

namespace JUpdator
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Eliminando JAR anterior");
            String JAR = "../../JRemoteControl.jar";
            try
            {
                // Check if file exists
                if (File.Exists(JAR))
                {
                    // If file found, delete it    
                    File.Delete(JAR);
                    Console.WriteLine("File deleted.");
                }
                else Console.WriteLine("File not found");
            }
            catch (IOException ioExp)
            {
                Console.WriteLine(ioExp.Message);
            }
            Console.WriteLine("Descargando");
            String downloadRoute = "https://github.com/aitorru/JRemoteControl/releases/latest/download/JRemoteControl.jar";
            try
            {
                using (var client = new WebClient())
                {
                    client.DownloadFile(downloadRoute, "../../JRemoteControl.jar");
                }
            }
            catch (System.Exception)
            {
                Console.WriteLine("No se ha podido descargar el archivo");
                throw;
            }
            Console.WriteLine("Archivo descargado");
        }
    }
}
