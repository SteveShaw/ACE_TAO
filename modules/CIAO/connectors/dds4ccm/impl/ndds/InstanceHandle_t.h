// $Id$

inline void
operator<<= (::DDS::InstanceHandle_t &ddsinstancehandle, const ::DDS_InstanceHandle_t & instancehandle)
{
  ACE_OS::memcpy (ddsinstancehandle.value, instancehandle.keyHash.value, MIG_RTPS_KEY_HASH_MAX_LENGTH);
  ddsinstancehandle.length = instancehandle.keyHash.length;
}

inline void
operator<<= (::DDS_InstanceHandle_t &ddsinstancehandle, const ::DDS::InstanceHandle_t & instancehandle)
{
  ACE_OS::memcpy (ddsinstancehandle.keyHash.value, instancehandle.value, MIG_RTPS_KEY_HASH_MAX_LENGTH);
  ddsinstancehandle.keyHash.length = instancehandle.length;
}

inline void
operator>>= (const ::DDS_InstanceHandle_t &instancehandle, ::DDS::InstanceHandle_t & ddsinstancehandle)
{
  ACE_OS::memcpy (ddsinstancehandle.value, instancehandle.keyHash.value, MIG_RTPS_KEY_HASH_MAX_LENGTH);
  ddsinstancehandle.length = instancehandle.keyHash.length;
}

inline void
operator>>= (const ::DDS::InstanceHandle_t &instancehandle, ::DDS_InstanceHandle_t & ddsinstancehandle)
{
  ACE_OS::memcpy (ddsinstancehandle.keyHash.value, instancehandle.value, MIG_RTPS_KEY_HASH_MAX_LENGTH);
  ddsinstancehandle.keyHash.length = instancehandle.length;
}

