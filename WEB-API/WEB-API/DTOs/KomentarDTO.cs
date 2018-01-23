using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WEB_API.Models;

namespace WEB_API.DTOs
{
    public class KomentarDTO
    {
        public virtual int IdKomentar { get; set; }
        public virtual DateTime Datum { get; set; }
        public virtual Korisnik Dodao { get; set; }
        public virtual string KomentarData { get; set; }
    }
}