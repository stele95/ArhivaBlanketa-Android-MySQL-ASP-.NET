using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class Slusa
    {
        public virtual SlusaID Id { get; set; }

        

        public Slusa()
        {
            Id = new SlusaID();
        }
    }
}