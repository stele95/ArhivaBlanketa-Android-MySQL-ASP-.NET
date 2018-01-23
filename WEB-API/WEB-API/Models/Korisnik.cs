using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.Models
{
    public class Korisnik
    {
        public virtual int IdKorisnik { get; set; }
        public virtual string Ime { get; set; }
        public virtual string Prezime { get; set; }
        public virtual int BrIndeksa { get; set; }
        public virtual string Username { get; set; }
        public virtual string Password { get; set; }
        public virtual string Email { get; set; }
        public virtual bool Admin { get; set; }
        public virtual Fakultet Fakultet { get; set; }

        

    }
}