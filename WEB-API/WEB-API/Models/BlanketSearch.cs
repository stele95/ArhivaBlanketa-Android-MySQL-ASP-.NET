using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class BlanketSearch
    {
        public virtual int IdPredmet { get; set; }
        public virtual int IdRok { get; set; }
        public virtual int Godina { get; set; }
        public virtual bool Pismeni { get; set; }
        public virtual bool Usmeni { get; set; }
    }
}