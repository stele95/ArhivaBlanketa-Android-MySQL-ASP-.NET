using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.DTOs
{
    public class PredmetDTO
    {
        public virtual int IdPredmet { get; set; }
        public virtual string Naziv { get; set; }
        public virtual SmerDTO Smer { get; set; }
        public virtual GodinaStudijaDTO Godina { get; set; }
    }
}