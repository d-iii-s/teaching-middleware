#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include <mpi.h>

int main (int iArgC, char *apArgV [])
{
  int iRank;
  int iLength;
  char *pMessage;
  char acMessage [] = "Hello World !";

  MPI_Init (&iArgC, &apArgV);

  MPI_Comm_rank (MPI_COMM_WORLD, &iRank);

  if (iRank == 0)
  {
    iLength = sizeof (acMessage);
    MPI_Bcast (&iLength, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast (acMessage, iLength, MPI_CHAR, 0, MPI_COMM_WORLD);
    printf ("Process 0: Message sent\n");
  }
  else
  {
    MPI_Bcast (&iLength, 1, MPI_INT, 0, MPI_COMM_WORLD);
    pMessage = (char *) malloc (iLength);
    MPI_Bcast (pMessage, iLength, MPI_CHAR, 0, MPI_COMM_WORLD);
    printf ("Process %d: %s\n", iRank, pMessage);
  }
    
  MPI_Finalize (); 
  
  return (0);
}
