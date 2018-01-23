using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class Smer
    {
        public virtual int IdSmer { get; set; }
        public virtual string Naziv { get; set; }
        public virtual Fakultet Fakultet { get; set; }

        

        
    }
}