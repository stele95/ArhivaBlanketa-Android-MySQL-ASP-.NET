using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class Prijava
    {
        public virtual int IdPrijava { get; set; }
        public virtual Blanket Blanket { get; set; }
        public virtual DateTime Datum { get; set; }
        public virtual Korisnik Dodao { get; set; }  
        public virtual string KomentarData { get; set; }
    }
}