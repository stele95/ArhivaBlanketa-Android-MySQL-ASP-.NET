using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class Predmet
    {
        public virtual int IdPredmet { get; set; }
        public virtual string Naziv { get; set; }
        public virtual Smer Smer { get; set; }
        public virtual GodinaStudija Godina { get; set; }

        
    }
}