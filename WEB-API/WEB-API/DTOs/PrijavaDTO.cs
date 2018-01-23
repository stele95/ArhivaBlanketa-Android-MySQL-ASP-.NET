using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.DTOs
{
    public class PrijavaDTO
    {
        public virtual int IdPrijava { get; set; }
        public virtual int Blanket { get; set; }        
        public virtual int Dodao { get; set; }
        public virtual string KomentarData { get; set; }
    }
}