using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class Fakultet
    {
        public virtual int IdFakultet { get; set; }
        public virtual string Naziv { get; set; }
        public virtual string Grad { get; set; }


    }
}