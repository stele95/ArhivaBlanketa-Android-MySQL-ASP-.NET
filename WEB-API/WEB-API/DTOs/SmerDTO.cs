using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.DTOs
{
    public class SmerDTO
    {
        public virtual int IdSmer { get; set; }
        public virtual string Naziv { get; set; }
        public virtual FakultetDTO Fakultet { get; set; }
    }
}