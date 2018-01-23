using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.DTOs
{
    public class KomentarPostDTO
    {
        public virtual int IdKomentar { get; set; }
        public virtual int ID { get; set; }
        public virtual DateTime Datum { get; set; }
        public virtual int Dodao { get; set; }
        public virtual bool PripadaBlanketu { get; set; }
        public virtual string KomentarData { get; set; }
    }
}