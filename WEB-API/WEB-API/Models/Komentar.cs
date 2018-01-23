using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class Komentar
    {
        public virtual int IdKomentar { get; set; }
        public virtual Blanket Blanket { get; set; }
        public virtual Resenje Resenje { get; set; }
        public virtual DateTime Datum { get; set; }
        public virtual Korisnik Dodao { get; set; }
        public virtual bool PripadaBlanketu { get; set; }
        public virtual string KomentarData { get; set; }

    }
}