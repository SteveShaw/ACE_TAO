// -*- C++ -*-
// $Id$
// ============================================================================
//
// = LIBRARY
//   TAO
//
// = FILENAME
//   Protocol_Factory.h
//
// = AUTHOR
//   Fred Kuhns <fredk@cs.wustl.edu>
//   Ossama Othman <othman@cs.wustl.edu>
//
// ============================================================================

#ifndef TAO_UIOP_FACTORY_H
#define TAO_UIOP_FACTORY_H

#include "tao/Protocol_Factory.h"

# if !defined (ACE_LACKS_UNIX_DOMAIN_SOCKETS)

#if !defined (ACE_LACKS_PRAGMA_ONCE)
# pragma once
#endif /* ACE_LACKS_PRAGMA_ONCE */

class TAO_Acceptor;
class TAO_Connector;


class TAO_Export TAO_UIOP_Protocol_Factory : public TAO_Protocol_Factory
{
public:
  TAO_UIOP_Protocol_Factory (void);
  virtual ~TAO_UIOP_Protocol_Factory (void);

  // = Service Configurator hooks.
  virtual int init (int argc, char* argv[]);
  // Dynamic linking hook

  virtual int match_prefix (const ACE_CString &prefix);
  // Verify prefix is a match

  virtual const char *prefix (void) const;
  // Returns the prefix used by the protocol.

  // Factory methods
  virtual TAO_Acceptor  *make_acceptor (void);
  // Create an acceptor

  virtual TAO_Connector *make_connector  (void);
  // Create a connector

private:
  int major_;
  int minor_;
  // Changing the version number can be used to provide backwards
  // compatibility with old clients.
};

ACE_STATIC_SVC_DECLARE (TAO_UIOP_Protocol_Factory)
ACE_FACTORY_DECLARE (TAO, TAO_UIOP_Protocol_Factory)

# endif  /* !ACE_LACKS_UNIX_DOMAIN_SOCKETS */

#endif /* TAO_UIOP_FACTORY_H */
